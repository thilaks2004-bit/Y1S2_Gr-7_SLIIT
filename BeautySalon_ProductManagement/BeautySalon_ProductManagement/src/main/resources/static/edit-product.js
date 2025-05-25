// Utility function to get URL parameter
function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

document.addEventListener('DOMContentLoaded', () => {
    const productId = getQueryParam('id');

    if (!productId) {
        Swal.fire({
            icon: 'error',
            title: 'Missing Product ID',
            text: 'No product ID provided in URL.',
        }).then(() => {
            window.location.href = "product-list.html";
        });
        return;
    }

    // ✅ Load product details from backend
    fetch(`/products/${productId}`)
        .then(res => {
            if (!res.ok) throw new Error('Product not found');
            return res.json();
        })
        .then(product => {
            document.getElementById("id").value = product.id;
            document.getElementById("name").value = product.name;
            document.getElementById("category").value = product.category;
            document.getElementById("price").value = product.price;
            document.getElementById("stock").value = product.stock;
            document.getElementById("image").value = product.image;
            document.getElementById("description").value = product.description;
        })
        .catch(err => {
            Swal.fire('Error', err.message, 'error').then(() => {
                window.location.href = "product-list.html";
            });
        });

    // ✅ Handle form submission
    document.getElementById("editForm").addEventListener("submit", function (e) {
        e.preventDefault();

        const form = this;
        if (!form.checkValidity()) {
            form.reportValidity();
            return;
        }

        Swal.fire({
            title: 'Are you sure?',
            text: 'Do you want to update this product?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, update it!',
            cancelButtonText: 'Cancel'
        }).then(result => {
            if (result.isConfirmed) {
                const updatedProduct = {
                    id: document.getElementById("id").value,
                    name: document.getElementById("name").value.trim(),
                    category: document.getElementById("category").value,
                    price: parseFloat(document.getElementById("price").value),
                    stock: parseInt(document.getElementById("stock").value),
                    image: document.getElementById("image").value.trim(),
                    description: document.getElementById("description").value.trim()
                };

                fetch(`/products/${updatedProduct.id}`, {
                    method: "PUT",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(updatedProduct)
                })
                    .then(res => {
                        if (!res.ok) throw new Error('Failed to update product.');
                        return res.text();
                    })
                    .then(() => {
                        Swal.fire({
                            icon: 'success',
                            title: 'Updated!',
                            text: 'Product updated successfully.',
                            timer: 2000,
                            showConfirmButton: false
                        }).then(() => {
                            window.location.href = "product-list.html";
                        });
                    })
                    .catch(err => {
                        Swal.fire('Error', err.message, 'error');
                    });
            }
        });
    });
});
