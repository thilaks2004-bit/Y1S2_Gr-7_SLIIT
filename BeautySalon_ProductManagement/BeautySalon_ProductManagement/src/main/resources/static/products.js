// Function to fetch products from backend API and display them
async function loadProducts() {
    try {
        const response = await fetch('/products');  // GET /products from backend
        const products = await response.json();

        const tbody = document.querySelector('#productTable tbody');
        tbody.innerHTML = '';  // Clear existing rows

        products.forEach(product => {
            const tr = document.createElement('tr');

            tr.innerHTML = `
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.stock}</td>
                <td>
                    <button onclick="editProduct('${product.id}')">Edit</button>
                    <button onclick="deleteProduct('${product.id}')">Delete</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Error loading products:', error);
    }
}

// Call loadProducts when the page loads
window.onload = loadProducts;

function editProduct(id) {
    // Redirect to edit page with the product ID as a URL parameter
    window.location.href = `edit-product.html?id=${id}`;
}


function deleteProduct(id) {
    if (confirm('Are you sure you want to delete this product?')) {
        fetch(`/products/${id}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    alert('Product deleted successfully.');
                    loadProducts();  // Refresh the product list after delete
                } else {
                    alert('Failed to delete product.');
                }
            })
            .catch(error => {
                alert('Error: ' + error.message);
            });
    }
}

