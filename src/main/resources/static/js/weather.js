function celsiusToFahrenheit(c) {
    return Math.round((c * 9 / 5 + 32) * 10) / 10;
}

function fahrenheitToCelsius(f) {
    return Math.round(((f - 32) * 5 / 9) * 10) / 10;
}

function toggleUnit() {
    var isF = localStorage.getItem('tempUnit') === 'F';
    var newUnit = isF ? 'C' : 'F';
    localStorage.setItem('tempUnit', newUnit);
    applyTempUnit();
}

function applyTempUnit() {
    var isF = localStorage.getItem('tempUnit') === 'F';
    var btn = document.getElementById('unitToggle');
    if (btn) {
        btn.textContent = isF ? '°C' : '°F';
    }

    var tempDisplay = document.getElementById('tempDisplay');
    var tempUnit = document.getElementById('tempUnit');
    if (tempDisplay && tempUnit) {
        var celsius = parseFloat(tempDisplay.getAttribute('data-celsius'));
        if (!isNaN(celsius)) {
            if (isF) {
                tempDisplay.textContent = celsiusToFahrenheit(celsius);
                tempUnit.textContent = '°F';
            } else {
                tempDisplay.textContent = celsius;
                tempUnit.textContent = '°C';
            }
        }
    }

    var historyTemps = document.querySelectorAll('.history-temp');
    historyTemps.forEach(function(el) {
        var celsius = parseFloat(el.getAttribute('data-celsius'));
        var unitSpan = el.parentElement.querySelector('.temp-unit-label');
        if (!isNaN(celsius)) {
            if (isF) {
                el.textContent = celsiusToFahrenheit(celsius);
                if (unitSpan) unitSpan.textContent = '°F';
            } else {
                el.textContent = celsius;
                if (unitSpan) unitSpan.textContent = '°C';
            }
        }
    });
}

function addRecentCity(city) {
    var recent = JSON.parse(localStorage.getItem('recentCities') || '[]');
    recent = recent.filter(function(c) { return c.toLowerCase() !== city.toLowerCase(); });
    recent.unshift(city);
    if (recent.length > 5) recent = recent.slice(0, 5);
    localStorage.setItem('recentCities', JSON.stringify(recent));
}

function loadRecentCities() {
    var recent = JSON.parse(localStorage.getItem('recentCities') || '[]');
    var container = document.getElementById('recentCities');
    if (!container || recent.length === 0) return;
    var html = '';
    recent.forEach(function(city) {
        html += '<a href="/weather?city=' + encodeURIComponent(city) + '" class="btn btn-sm btn-outline-light m-1">' + city + '</a>';
    });
    container.innerHTML = html;
}

document.addEventListener('DOMContentLoaded', applyTempUnit);
