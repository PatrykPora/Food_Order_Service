function addItem(itemId) {
    fetch('/' + 'addItem/' + encodeURIComponent(itemId), {
        method: 'GET',
    })
        .then(response => response.text())
        .then(data => {
            alert(data)
        })
        .catch(error => {
            console.error('Error', error)
        });
}