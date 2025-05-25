const pageSize = 5;
let currentPage = 1;
let products = [];
let filteredProducts = [];

// Load products from backend
function loadProducts() {
    fetch("/products")
        .then(res => {
            if (!res.ok) throw new Error("Failed to fetch products");
            return res.json();
        })
        .then(data => {
            products = data;
            filteredProducts = products;
            currentPage = 1;
            renderTable();
            renderPagination();
        })
        .catch(err => {
            Swal.fire("Error", err.message, "error");
        });
}

// Render product table with pagination
function renderTable() {
    const tbody = document.getElementById("productTableBody");
    tbody.innerHTML = "";

    const start = (currentPage - 1) * pageSize;
    const end = start + pageSize;
    const pageItems = filteredProducts.slice(start, end);

    if (pageItems.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="text-center">No products found.</td></tr>';
        return;
    }

    pageItems.forEach(p => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${p.id}</td>
            <td><a href="product-details.html?id=${p.id}">${escapeHtml(p.name)}</a></td>
            <td>${escapeHtml(p.category)}</td>
            <td>$${p.price.toFixed(2)}</td>
            <td>
                <span class="${stockBadgeClass(p.stock)}">${p.stock}</span>
            </td>
            <td>
                <a href="edit-product.html?id=${p.id}" class="btn btn-sm btn-primary me-1">Edit</a>
                <button class="btn btn-sm btn-danger" onclick="deleteProduct('${p.id}')">Delete</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// Render pagination controls
function renderPagination() {
    const pagination = document.getElementById("pagination");
    pagination.innerHTML = "";

    const totalPages = Math.ceil(filteredProducts.length / pageSize);
    if (totalPages <= 1) return;

    // Previous button
    const prevLi = document.createElement("li");
    prevLi.className = "page-item " + (currentPage === 1 ? "disabled" : "");
    prevLi.innerHTML = `<a class="page-link" href="#" tabindex="-1">Previous</a>`;
    prevLi.addEventListener("click", e => {
        e.preventDefault();
        if (currentPage > 1) {
            currentPage--;
            renderTable();
            renderPagination();
        }
    });
    pagination.appendChild(prevLi);

    // Page numbers
    for (let i = 1; i <= totalPages; i++) {
        const li = document.createElement("li");
        li.className = "page-item " + (i === currentPage ? "active" : "");
        li.innerHTML = `<a class="page-link" href="#">${i}</a>`;
        li.addEventListener("click", e => {
            e.preventDefault();
            currentPage = i;
            renderTable();
            renderPagination();
        });
        pagination.appendChild(li);
    }

    // Next button
    const nextLi = document.createElement("li");
    nextLi.className = "page-item " + (currentPage === totalPages ? "disabled" : "");
    nextLi.innerHTML = `<a class="page-link" href="#">Next</a>`;
    nextLi.addEventListener("click", e => {
        e.preventDefault();
        if (currentPage < totalPages) {
            currentPage++;
            renderTable();
            renderPagination();
        }
    });
    pagination.appendChild(nextLi);
}

// Delete product function with SweetAlert confirmation
function deleteProduct(id) {
    Swal.fire({
        title: 'Delete product?',
        text: "Are you sure you want to delete this product?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, delete it!',
        cancelButtonText: 'Cancel'
    }).then(result => {
        if (result.isConfirmed) {
            fetch(`/products/${id}`, { method: "DELETE" })
                .then(res => {
                    if (!res.ok) throw new Error("Failed to delete product");
                    return res.text();
                })
                .then(msg => {
                    Swal.fire('Deleted!', 'Product has been deleted.', 'success');
                    loadProducts();
                })
                .catch(err => {
                    Swal.fire('Error', err.message, 'error');
                });
        }
    });
}

// Search input filter by name or id
document.getElementById("searchInput").addEventListener("input", function() {
    const query = this.value.trim().toLowerCase();
    filteredProducts = products.filter(p =>
        p.name.toLowerCase().includes(query) ||
        String(p.id).toLowerCase().includes(query)
    );
    currentPage = 1;
    renderTable();
    renderPagination();
});

// Utility for stock badge classes based on stock level
function stockBadgeClass(stock) {
    if (stock > 10) return "badge bg-success";
    if (stock > 0) return "badge bg-warning text-dark";
    return "badge bg-danger";
}

// Escape HTML to prevent XSS
function escapeHtml(text) {
    return text
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}

// Initial load
loadProducts();
function renderProducts(products) {
    const tableBody = document.getElementById('productTableBody');
    tableBody.innerHTML = "";

    products.forEach(product => {
        const row = document.createElement('tr');

        row.innerHTML = `
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.category}</td>
            <td>${product.price}</td>
            <td>${product.stock}</td>
            <td>
                <a href="/products/edit/${product.id}" class="btn btn-warning btn-sm">Edit</a>
                <button onclick="deleteProduct('${product.id}')" class="btn btn-danger btn-sm">Delete</button>
            </td>
        `;

        tableBody.appendChild(row);
    });
}


