package sd.urjc.proyecto.controller;

import javax.annotation.PostConstruct;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sd.urjc.proyecto.model.Producto;
import sd.urjc.proyecto.repository.ProductoRepository;

@Controller
public class ProductoController {

	@Autowired
	private ProductoRepository repProductos;
	
	@PostConstruct
	public void init() {
		repProductos.save(new Producto("Microtox", "Fungicida-acaricida a base de azufre en forma de gránulos dispersables en agua.", 10, 14));
		repProductos.save(new Producto("Captana", "Fungicida que actúa inhibiendo la germinación de las esporas, dificultando el crecimiento y desarrollo del micelo.", 9, 5));
		repProductos.save(new Producto("Adrex", "Miscible con todo tipo de productos y puede aplicarse sobre todos los cultivos.", 4, 8));
	}
	
	@RequestMapping("/productos/")
	public String mostrarProductos (Model model) {
		model.addAttribute("productos", repProductos.findAll());
		return "productos";
	}
	
	@RequestMapping("/productos/nuevoProducto")
	public String añadirProducto (Producto producto, Model model) {
		repProductos.save(producto);
		return "creado";
	}
	
	@RequestMapping("/productos/modificar/{id}")
	public String solicitarModificarProducto(@PathVariable String id, Model model) {
		Optional<Producto> opt= repProductos.findById(Long.parseLong(id));
		if (opt.isPresent()) {
			model.addAttribute("producto", opt.get());
			return "modificar";
		}
		else {
			return "productos";
		}
	}
	
	@RequestMapping("productos/editar/{id}")
	public String editarProducto(@PathVariable String id, Producto productoModificado, Model model) {
		Optional<Producto> opt= repProductos.findById(Long.parseLong(id));
		Producto producto;
		if (opt.isPresent()) {
			 producto= opt.get();
			 producto.setNombre(productoModificado.getNombre());
			 producto.setDescripcion(productoModificado.getDescripcion());
			 producto.setPlazoReentrada(productoModificado.getPlazoReentrada());
			 producto.setPlazoRecoleccion(productoModificado.getPlazoRecoleccion());
			 repProductos.save(producto);
			 return "editado";
		}
		else {
			return "productos";
		}
	}
	
	@RequestMapping("/productos/mostrar/{id}")
	public String consultarProducto (
			@PathVariable String id,			
			Model model){		
		Optional<Producto> opt= repProductos.findById(Long.parseLong(id));
		if(opt.isPresent()) {			
			model.addAttribute("producto", opt.get());
			return "mostrar";
		}else {
			return "productos";
		}
	}
}
