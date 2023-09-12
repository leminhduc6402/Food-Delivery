document.addEventListener('DOMContentLoaded', function () {
    const yearInput = document.getElementById('year');
    const quarterSelect = document.getElementById('quarter');
    const monthSelect = document.getElementById('month');

    quarterSelect.disabled = true;
    monthSelect.disabled = true;

    yearInput.addEventListener('input', function () {
        const yearValue = this.value.trim();

        if (yearValue) {
            quarterSelect.disabled = false;
            monthSelect.disabled = false;
        } else {
            quarterSelect.disabled = true;
            monthSelect.disabled = true;
        }
    });

    quarterSelect.addEventListener('change', function() {
        if (this.value) {
            monthSelect.disabled = true;
        } else {
            monthSelect.disabled = false;
        }
    });

    monthSelect.addEventListener('change', function() {
        if (this.value) {
            quarterSelect.disabled = true;
        } else {
            quarterSelect.disabled = false;
        }
    });
});
