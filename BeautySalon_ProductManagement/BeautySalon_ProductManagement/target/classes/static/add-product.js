document.getElementById('addProductForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const product = {
        id: document.getElementById('id').value,
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
        price: parseFloat(document.getElementById('price').value),
        stock: parseInt(document.getElementById('stock').value, 10)
    };

    fetch('/products', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(product)
    })
        .then(response => {
            if (response.ok) {
                alert('Product added successfully!');
                window.location.href = 'product-list.html';  // redirect to product list page
            } else {
                alert('Failed to add product.');
            }
        })
        .catch(error => {
            alert('Error: ' + error.message);
        });
});
