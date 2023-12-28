package com.crud.crudSpringBackend.service;


import com.crud.crudSpringBackend.entity.Producto;
import com.crud.crudSpringBackend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    public List<Producto> list(){
        return productoRepository.findAll();
    }

    public Optional<Producto> getProductoById(int id){
        return productoRepository.findById(id);
    }

    public Optional<Producto> getProductoByNombre(String nombre){
        return productoRepository.findByNombre(nombre);
    }

    public void createProducto(Producto producto){
        productoRepository.save(producto);
    }

    public void deleteProducto(int id){
        productoRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return productoRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre){
        return productoRepository.existsByNombre(nombre);
    }

}
