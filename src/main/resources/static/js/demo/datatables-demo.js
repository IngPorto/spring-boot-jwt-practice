// Call the dataTables jQuery plugin
$(document).ready(function() {
    cargarUsuarios();
    $('#dataTable').DataTable();
});

const cargarUsuarios = async ()=> {
    const rawResponse = await fetch('/api/search', {
        method:'GET',
        headers:{
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')
        }
    });

    const usuarios = await rawResponse.json();

    const fila_usuarios = usuarios.map(usuario => {
        let fila = document.createElement('tr');
        fila.innerHTML  = `<tr>
            <td>${usuario.id}</td>
            <td>${usuario.nombre} ${usuario.apellido}</td>
            <td>${usuario.email}</td>
            <td>${usuario.telefono}</td>
            <td>
                <a
                    id="trash-btn"
                    href="#"
                    class="btn btn-danger btn-circle btn-sm"
                    onclick="deleteItem(this)"
                    data-id="${usuario.id}"
                >
                    <i class="fas fa-trash"></i>
                </a>
            </td>
        </tr>
        `;
        document.querySelector("#dataTable tbody").appendChild(fila);
        return fila;
    })


}

const deleteItem = async item => {
    if(!confirm("¿Desea eliminar definitivamente este usuario?")){
        return;
    }
    const userId = item.getAttribute("data-id")
    await fetch('/api/delete/'+userId, {
        method:'DELETE',
        headers:{
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')
        },
        body: JSON.stringify({id: userId})
    }).them(res =>{
        switch(res.status){
            case 200: successLogin(res) ;break;
            case 202: successLogin(res) ;break;
            case 400: errorUserLogin(res) ;break;
            case 401: errorUserLogin(res) ;break;
            case 500: errorServerLogin(res) ;break;
        }
    })

    const successLogin = async (response) => {
        location.reload();
    }

    const errorUserLogin = (response) => {
        alert(`Error ${response.status}: Sin autorización`)
    }

    const errorServerLogin = (response) => {
        alert(`Error ${response.status}: Problemas en el servidor.`)
    }


}