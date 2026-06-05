package MetodologiaDeSistema.Proyecto.feature.Cupon.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import MetodologiaDeSistema.Proyecto.feature.Cupon.Models.Cupon;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Models.CuponCliente;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Models.CuponMonto;
import MetodologiaDeSistema.Proyecto.feature.Cupon.Models.CuponPorcentaje;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailCupon(CuponCliente cuponCliente) {
        Cupon cupon = cuponCliente.getCupon();
        String email = cuponCliente.getCliente().getEmail();
        String nombre = cuponCliente.getCliente().getNombre();

        String asunto = "¡Tu cupón de descuento ha llegado!";
        String mensaje = construirMensaje(nombre, cupon);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(asunto);
        mailMessage.setText(mensaje);

        mailSender.send(mailMessage);
    }

    private String construirMensaje(String nombre, Cupon cupon) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Hola ").append(nombre).append(",\n\n");
        mensaje.append("¡Tenemos una oferta especial para ti!\n\n");
        mensaje.append("Código del cupón: ").append(cupon.getCodigo()).append("\n");
        mensaje.append("Válido desde: ").append(cupon.getFechaInicio()).append("\n");
        mensaje.append("Válido hasta: ").append(cupon.getFechaVencimiento()).append("\n\n");

        if (cupon instanceof CuponPorcentaje) {
            CuponPorcentaje cp = (CuponPorcentaje) cupon;
            mensaje.append("Descuento: ").append(cp.getPorcentaje()).append("%\n\n");
        } else if (cupon instanceof CuponMonto) {
            CuponMonto cm = (CuponMonto) cupon;
            mensaje.append("Descuento: $").append(cm.getMontoDescuento()).append("\n");
            mensaje.append("Compra mínima: $").append(cm.getMontoMinimoCompra()).append("\n\n");
        }

        mensaje.append("¡No dejes pasar esta oportunidad!\n\n");
        mensaje.append("Saludos,\nEl equipo");

        return mensaje.toString();
    }
}