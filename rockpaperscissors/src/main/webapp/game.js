document.getElementById('playRoundButton').addEventListener('click', function() {
    fetch('/game/play/random/rock')
        .then(response => response.json())
        .then(data => {
            // Assuming the Result object includes player choices and the outcome
            const newRound = document.createElement('li');
            newRound.textContent = `Round: ${data.roundNo}, Player 1 chose ${data.player1Choice}, Player 2 chose ${data.player2Choice} — ${data.outcome}`;
            document.getElementById('roundsList').appendChild(newRound);
        })
        .catch(error => console.error('Failed to play round:', error));
});

document.getElementById('restartGameButton').addEventListener('click', function() {
    fetch('/game/restart')
        .then(() => {
            // Clear the rounds list
            document.getElementById('roundsList').innerHTML = '';
        })
        .catch(error => console.error('Failed to restart game:', error));
});

function loadRounds() {
    fetch('/game/rounds')
        .then(response => response.json())
        .then(data => {
            const roundsList = document.getElementById('roundsList');
            roundsList.innerHTML = ''; // Clear existing rounds
            data.forEach(result => {
                const round = document.createElement('li');
                round.textContent = `Round: ${result.roundNo}, Player 1 chose ${result.player1Choice}, Player 2 chose ${result.player2Choice} — ${result.outcome}`;
                roundsList.appendChild(round);
            });
        })
        .catch(error => console.error('Failed to load rounds:', error));
}

// Load rounds when the page is loaded
window.onload = loadRounds;
