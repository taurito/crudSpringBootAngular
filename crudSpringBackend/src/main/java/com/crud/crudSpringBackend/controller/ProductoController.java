package com.crud.crudSpringBackend.controller;

import com.crud.crudSpringBackend.dto.Mensaje;
import com.crud.crudSpringBackend.dto.ProductoDTO;
import com.crud.crudSpringBackend.entity.Producto;
import com.crud.crudSpringBackend.service.ProductoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/lista")
    public ResponseEntity<List<Producto>> listProductos(){
        List<Producto> productos = productoService.list();
        return new ResponseEntity(productos, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") int id){
        if(!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("El producto no existe"), HttpStatus.NOT_FOUND);
        Producto producto = productoService.getProductoById(id).get();
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @GetMapping("/detail/name/{nombre}")
    public ResponseEntity<?> getByNombre(@PathVariable("nombre") String nombre){
        if(!productoService.existsByNombre(nombre)){
            return new ResponseEntity<>(new Mensaje("El producto no existe"), HttpStatus.NOT_FOUND);
        }
        else{
            Producto producto = productoService.getProductoByNombre(nombre).get();
            return new ResponseEntity<>(producto, HttpStatus.OK);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> createProducto(@RequestBody ProductoDTO productoDto){
        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity<>( new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoDto.getPrecio()<0)
            return new ResponseEntity<>( new Mensaje("El precio debe ser mayor a 0"), HttpStatus.BAD_REQUEST);
        if(productoService.existsByNombre(productoDto.getNombre()))
            return new ResponseEntity<>( new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);

        Producto producto = new Producto(productoDto.getNombre(), productoDto.getPrecio());
        productoService.createProducto(producto);
        return new ResponseEntity<>(new Mensaje("Producto creado exitosamente"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProducto(@PathVariable("id") int id, @RequestBody ProductoDTO productoDto){
        if(!productoService.existsById(id))
            return new ResponseEntity<>(new Mensaje("Este producto no existe"), HttpStatus.NOT_FOUND);

        if(productoService.existsByNombre(productoDto.getNombre()) && productoService.getProductoByNombre(productoDto.getNombre()).get().getIdProducto() != id)
            return new ResponseEntity<>(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);

        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity<>( new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        if(productoDto.getPrecio()<0)
            return new ResponseEntity<>( new Mensaje("El precio debe ser mayor a 0"), HttpStatus.BAD_REQUEST);

        Producto producto = productoService.getProductoById(id).get();
        producto.setNombre(productoDto.getNombre());
        producto.setPrecio(productoDto.getPrecio());
        productoService.createProducto(producto);

        return new ResponseEntity<>(new Mensaje("producto actualizado"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable("id") int id){
        if(!productoService.existsById(id))
            return new ResponseEntity<>(new Mensaje("Este producto no existe"), HttpStatus.NOT_FOUND);

        productoService.deleteProducto(id);
        return new ResponseEntity<>(new Mensaje("Producto eliminado"), HttpStatus.OK);
    }
}