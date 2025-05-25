const urlParams = new URLSearchParams(window.location.search);
const productId = urlParams.get("id");

if (!productId) {
    Swal.fire({
        icon: 'error',
        title: 'Missing product ID',
        text: 'No product ID specified.',
    }).then(() => {
        window.location.href = "product-list.html";
    });
} else {
    fetch(`/products/${productId}`)
        .then(res => {
            if (!res.ok) throw new Error('Product not found');
            return res.json();
        })
        .then(product => {
            document.getElementById("productId").textContent = product.id;
            document.getElementById("productName").textContent = product.name;
            document.getElementById("productCategory").textContent = product.category;
            document.getElementById("productPrice").textContent = product.price.toFixed(2);
            document.getElementById("productStock").textContent = product.stock;
            document.getElementById("productDescription").textContent = product.description;
            document.getElementById("productImage").src = product.image;
            document.getElementById("productImage").alt = product.name;
        })
        .catch(err => {
            Swal.fire('Error', err.message, 'error').then(() => {
                window.location.href = "product-list.html";
            });
        });
}
