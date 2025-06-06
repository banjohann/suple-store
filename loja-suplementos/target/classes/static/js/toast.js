document.addEventListener('DOMContentLoaded', function() {
    const toast = document.getElementById('toast');
    if (toast) {
        setTimeout(() => {
            dismissToast();
        }, 5000);
    }
});

function showToast(message) {
    const toast = document.getElementById('toast');
    const messageSpan = document.getElementById('error-message');

    messageSpan.innerText = message;
    toast.classList.remove('hidden');
    setTimeout(() => {
        dismissToast();
    }, 15000);
}

function dismissToast() {
    const toast = document.getElementById('toast');
    toast.classList.add('hidden');
}