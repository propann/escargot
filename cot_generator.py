import socket
import time
import meshtastic.serial_interface

# 🗺️ CONFIGURATION RESEAU ATAK
# Pour que ATAK voie les points, il faut envoyer les paquets UDP sur le réseau.
# Option 1 : '255.255.255.255' (Broadcast global) - Le plus simple, ça arrose tout le monde.
# Option 2 : L'IP de votre téléphone (ex: '192.168.1.15') - Plus discret.
# Port 8087 est le port standard pour l'entrée UDP dans ATAK.
BROADCAST_IP = '255.255.255.255'
ATAK_PORT = 8087

def get_cot_xml(uid, name, lat, lon):
    """
    Génère un paquet XML Cursor-on-Target (CoT) valide.
    C'est le langage que parlent les militaires (et ATAK).
    """
    now = time.strftime("%Y-%m-%dT%H:%M:%SZ", time.gmtime())
    # Le point est valide 5 minutes (stale)
    stale = time.strftime("%Y-%m-%dT%H:%M:%SZ", time.gmtime(time.time() + 300))
    
    # Type: a-f-G-U-C (Atom - Friendly - Ground - Unit - Combat) -> Ami au sol
    # UID: Doit être unique pour éviter les fantômes sur la carte
    xml = f'''<?xml version='1.0' standalone='yes'?>
<event version="2.0" uid="Escargot-{uid}" type="a-f-G-U-C" time="{now}" start="{now}" stale="{stale}" how="m-g">
    <point lat="{lat}" lon="{lon}" hae="0" ce="9999999" le="9999999"/>
    <detail>
        <contact callsign="{name}" endpoint="" />
        <remarks>Escargot Node</remarks>
        <__group name="Escargot Mesh" role="Blue"/>
    </detail>
</event>'''
    return xml

def broadcast_cot(xml):
    """Diffuse le paquet XML sur le réseau via UDP."""
    try:
        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        sock.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)
        sock.sendto(xml.encode('utf-8'), (BROADCAST_IP, ATAK_PORT))
        sock.close()
        # print(f"📡 CoT Envoyé -> {BROADCAST_IP}:{ATAK_PORT}") # Debug
    except Exception as e:
        print(f"❌ Erreur UDP: {e}")

def main():
    print("🗺️ ATAK GENERATOR: Initialisation du radar tactique...")
    print(f"🎯 Cible de diffusion: {BROADCAST_IP}:{ATAK_PORT}")
    
    try:
        # Connexion au noeud Meshtastic local via USB
        interface = meshtastic.serial_interface.SerialInterface()
        print("✅ Meshtastic connecté. Scanning des positions...")

        while True:
            # On parcourt la table des noeuds connus (NodeDB)
            if interface.nodes:
                for node_id, node in interface.nodes.items():
                    # On ne traite que ceux qui ont une position valide
                    if 'position' in node and 'latitude' in node['position'] and 'longitude' in node['position']:
                        lat = node['position']['latitude']
                        lon = node['position']['longitude']
                        
                        # Récupération du nom (LongName > ShortName > ID)
                        user = node.get('user', {})
                        name = user.get('longName', user.get('shortName', node_id))
                        
                        print(f"📍 CIBLE: {name} @ [{lat}, {lon}] -> Envoi ATAK")
                        xml_payload = get_cot_xml(node_id, name, lat, lon)
                        broadcast_cot(xml_payload)
            
            # Pause tactique pour ne pas saturer le réseau (Refresh toutes les 10s)
            time.sleep(10)

    except Exception as e:
        print(f"💀 ERREUR CRITIQUE: {e}")
        print("Vérifiez que le Meshtastic est bien branché en USB.")

if __name__ == "__main__":
    main()