from flask import Flask, render_template
import sqlite3

app = Flask(__name__)
# Chemin relatif vers la DB générée par le bridge dans backend/bridge/
DB_PATH = "../../backend/bridge/escargot.db"

def get_messages():
    conn = sqlite3.connect(DB_PATH)
    conn.row_factory = sqlite3.Row
    c = conn.cursor()
    c.execute("SELECT * FROM messages ORDER BY timestamp DESC LIMIT 50")
    messages = c.fetchall()
    conn.close()
    return messages

@app.route('/')
def index():
    messages = get_messages()
    return render_template('index.html', messages=messages)

if __name__ == '__main__':
    # Host 0.0.0.0 pour être accessible sur le réseau local (WiFi du bunker)
    app.run(debug=True, host='0.0.0.0', port=5000)