document.getElementById("playButton").addEventListener("click", function() {
    const formData = new FormData(document.getElementById("gameForm"));
    const playerChoice = formData.get("playerChoice");

    // Assuming the API endpoint is '/game/play/p1' and it expects a POST request
    // with a body containing the player's choice
    fetch("/game/play/p1", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ playerChoice: playerChoice })
    })
        .then(response => response.json())
        .then(data => {
            // Display the result
            document.getElementById("result").innerHTML = `
            <p>Player 1 chose: ${data.player1Choice}</p>
            <p>Player 2 chose: ${data.player2Choice}</p>
            <p>Result: ${data.outcome}</p>
        `;
        })
        .catch(error => console.error("Error:", error));
});
