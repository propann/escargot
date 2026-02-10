import ollama

def analyze_text(text):
    """
    Analyse un message de survie avec un LLM local.
    Retourne : (Catégorie, Résumé)
    """
    # Prompt système optimisé pour la survie (et pour TinyLlama)
    prompt = (
        f"Tu es une IA de gestion de crise. "
        f"Classe ce message dans une seule catégorie : [URGENCE, SOCIAL, COMMERCE, INFOS]. "
        f"Fais un résumé de 5 mots max. "
        f"Format de réponse : 'CATEGORIE | Résumé'. "
        f"Message : '{text}'"
    )

    try:
        # Utilisation de tinyllama car il tourne bien sur Raspberry Pi
        response = ollama.chat(model='tinyllama', messages=[
            {'role': 'user', 'content': prompt},
        ])
        
        content = response['message']['content'].strip()
        
        # Parsing robuste (au cas où l'IA hallucine un peu)
        if "|" in content:
            parts = content.split("|", 1)
            return parts[0].strip().upper(), parts[1].strip()
        else:
            return "INFOS", content[:50]
            
    except Exception as e:
        print(f"⚠️ IA HS (Ollama): {e}")
        return "INCONNU", "Erreur analyse IA"