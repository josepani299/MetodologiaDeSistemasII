const productosContainer = document.getElementById("productosContainer");
const totalSpan = document.getElementById("total");

const carritoId = 1;

async function cargarCarrito() {

    const response = await fetch("http://localhost:8080/carrito/" + carritoId);

    const carrito = await response.json();

    productosContainer.innerHTML = "";

    carrito.items.forEach(item => {

        const productoDiv = document.createElement("div");

        productoDiv.classList.add("producto");

        productoDiv.innerHTML =
            "<div class='producto-info'>" +
                "<h3>" + item.producto.nombre + "</h3>" +
                "<p>Precio: $" + item.precio + "</p>" +
                "<p>Cantidad: " + item.cantidad + "</p>" +
            "</div>" +

            "<div class='producto-acciones'>" +
                "<button class='btn-sumar' onclick='sumarCantidad(" + item.id + "," + item.cantidad + ")'>+</button>" +
                "<button class='btn-restar' onclick='restarCantidad(" + item.id + "," + item.cantidad + ")'>-</button>" +
                "<button class='btn-eliminar' onclick='eliminarProducto(" + item.id + ")'>Eliminar</button>" +
            "</div>";

        productosContainer.appendChild(productoDiv);
    });

    totalSpan.textContent = carrito.total;
}

async function sumarCantidad(itemId, cantidadActual) {

    await fetch("http://localhost:8080/carrito/items/" + itemId + "?cantidad=" + (cantidadActual + 1), {
        method: 'PUT'
    });

    cargarCarrito();
}

async function restarCantidad(itemId, cantidadActual) {

    if(cantidadActual <= 1){
        return;
    }

    await fetch("http://localhost:8080/carrito/items/" + itemId + "?cantidad=" + (cantidadActual - 1), {
        method: 'PUT'
    });

    cargarCarrito();
}

async function eliminarProducto(itemId) {

    await fetch("http://localhost:8080/carrito/items/" + itemId, {
        method: 'DELETE'
    });

    cargarCarrito();
}

cargarCarrito();