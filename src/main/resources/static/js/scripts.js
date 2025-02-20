function addItem(itemName) {
    fetch('/' + 'addItem/' + encodeURIComponent(itemName), {
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