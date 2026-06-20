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

document.addEventListener('DOMContentLoaded', applyTempUnit);
