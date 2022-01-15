// Call the dataTables jQuery plugin
$(document).ready(function() {
  getUsers();
  $('#tblUsuarios').DataTable();
  actualizarEmailUser();
});

function actualizarEmailUser(){
    document.getElementById('txtEmailUser').outerHTML = localStorage.email;
}

async function getUsers(){
        const request = await fetch('api/usuarios', {
        method: 'GET',
        headers: getHeaders(),
      });
      const users = await request.json();
        let listadoHtml = '';
  for (let usuario of users) {
    let botonEliminar = '<a href="#" onclick="removeUser(' + usuario.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';

    let telefonoTexto = usuario.telefono == null ? '-' : usuario.telefono;
    let usuarioHtml = '<tr><td>'+usuario.id+'</td><td>' + usuario.nombre + ' ' + usuario.apellido + '</td><td>'
                    + usuario.email+'</td><td>'+telefonoTexto
                    + '</td><td>' + botonEliminar + '</td></tr>';
    listadoHtml += usuarioHtml;
  }
     document.querySelector('#tblUsuarios tbody').outerHTML = listadoHtml;
}


function getHeaders() {
  return  {
    'Accept': 'application/json',
    'Content-Type': 'application/json',
    'Authorization': localStorage.token
  }
}

async function removeUser(id){
  if (!confirm('¿Desea eliminar este usuario?')) {
    return;
  }

 const request = await fetch('api/usuarios/' + id, {
    method: 'DELETE',
            headers: getHeaders()
  });

  location.reload();
     }