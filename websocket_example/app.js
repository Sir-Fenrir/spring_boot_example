// We initialiseren de Stomp client. Deze wordt gedownload in de index.html (regel 41)
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/websocket' // Het protocol is niet HTTP, maar WS. Dit is de URL naar onze lokale server.
});

// Hier stellen we in wat er moet gebeuren zodra er een connectie met de server wordt gemaakt.
stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);

    // Hier zeggen we wat er moet gebeuren als er een nieuw bericht komt van de server.
    stompClient.subscribe('/topic/messages', (message) => {
        // Oké, dit ziet er misschien wat gek uit, ik leg het even uit per regel:
        showMessage( // We zeggen dat de showMessage functie aangeroepen worden
            JSON.stringify( // Met JSON.stringify probeer ik een JavaScript object om te zetten naar nette JSON...
                JSON.parse(message.body), // ... maar de body van de response is al een string, waarin alles op één regel staat. Dus ik parse het eerst naar een JavaScript object, om het daarna weer netjes te maken met JSON.stringify.
                null, // Niet relevant, is nodig voor het 'pretty printen'
                2) // De indentatie
        );
    });
};

// Even zeggen wat er moet gebeuren als er een error
stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

// En een bericht error
stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

// In deze functie triggeren we welke knoppen bruikbaar zijn. Probeer het maar eens in de browser, dan zie je wat er gebeurt.
function setConnected(connected) {
    document.querySelector('#connect').disabled = connected;
    document.querySelector('#disconnect').disabled = !connected;
}

// Leg de connectie!
function connect() {
    stompClient.activate();
}

// We stoppen de connectie.
function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log('Disconnected');
}

// Deze functie wordt gebruikt om daadwerkelijk een bericht te sturen.
function sendMessage() {
    console.log('Sending');

    stompClient.publish({
        destination: '/messages/broadcast', // De endpoint waar het heen moet.
        body: JSON.stringify({ // We maken een json string van de twee invoer velden
            'title': document.querySelector('#title').value,
            'content': document.querySelector('#content').value
        })
    });
}

// Elk bericht dat binnenkomt, wordt toegevoegd aan de HTML.
function showMessage(message) {
    document.querySelector('#messages').textContent += `${message}\n\n`;
}

// Dit is een 'Immediately invoked function execution', een functie die automatisch wordt uitgevoerd.
// Dit is niet per se nodig, maar wordt in de praktijk wel vaak gebruik.
(function () {
    [].slice.call(document.getElementsByTagName('form')).forEach(tag => tag.addEventListener('submit', (e) => e.preventDefault()));
    document.querySelector('#connect').addEventListener('click', () => connect());
    document.querySelector('#disconnect').addEventListener('click', () => disconnect());
    document.querySelector('#send').addEventListener('click', () => sendMessage());
})();