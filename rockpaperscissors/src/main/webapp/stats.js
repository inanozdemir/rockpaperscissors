document.addEventListener("DOMContentLoaded", function() {
    fetchStats();
});

function fetchStats() {
    fetch('/stats')
        .then(response => response.json())
        .then(data => updateStatsUI(data))
        .catch(error => console.error('Error fetching game statistics:', error));
}

function updateStatsUI(stats) {
    document.getElementById('totalRoundsPlayed').textContent = stats.totalRoundsPlayed;
    document.getElementById('totalWinsForFirstPlayer').textContent = stats.totalWinsForFirstPlayer;
    document.getElementById('totalWinsForSecondPlayer').textContent = stats.totalWinsForSecondPlayer;
    document.getElementById('totalDraws').textContent = stats.totalDraws;
}
