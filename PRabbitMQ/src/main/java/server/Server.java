package server;

import bolsa.Bolsa;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import definiciones.Definiciones;
import modelos.Empresa;
import herramientas.Herramientas;
import java.io.IOException;
import static java.lang.Thread.sleep;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import modelos.Alarma;
import modelos.Cliente;

public class Server {
    // se define el nombre de la cola
    private final static String QUEUE_NAME_SERVER = Definiciones.QUEUE_NAME_SERVER;
    
    private static List<Empresa> listEmpresas;
    
    private static List<Cliente> listClientes;
    
    private static Cliente clienteActual;
    
    private static Integer contAlarmas;
    
    private static Channel channel;

    public static void main(String[] argv) throws Exception {
        listEmpresas = Bolsa.getDatos();
        listClientes = new ArrayList<>();
        contAlarmas = 0;
        try{
            // creamos la conexión al servidor
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME_SERVER, false, false, false, null);
            System.out.println(" [*] Esperando mensajes, para salir pulsa CTRL-C");
            // Almacena los objetos en un buffer hasta que sean utilizados
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String messageRecibido = new String(delivery.getBody(), "UTF-8");
                try{
                    System.out.println("Mensaje recibido: " + messageRecibido);
                    String messageEnvio = "";
                    String cliente = Herramientas.getCliente(messageRecibido);
                    actualizarClienteActual(cliente);
                    System.out.println("cliente: " + cliente);
                    switch(Herramientas.getTipo(messageRecibido)){
                        case Definiciones.TIPO_SUBSCRIPCION:
                            if(clienteActual == null){
                                clienteActual =  nuevoCliente(cliente);
                                channel.queueDeclare(cliente, false, false, false, null);
                            }
                            messageEnvio = Herramientas.generarMensaje(Definiciones.TIPO_SUBSCRIPCION, cliente, Empresa.getListEmpresasToString(listEmpresas),Alarma.getListAlarmasToString(clienteActual.getAlarmas()));
                            break;
                        case Definiciones.TIPO_LISTA_EMPRESAS:
                            messageEnvio = Herramientas.generarMensaje(Definiciones.TIPO_SUBSCRIPCION, cliente, Empresa.getListEmpresasToString(listEmpresas),Alarma.getListAlarmasToString(clienteActual.getAlarmas()));
                            break;
                        case Definiciones.TIPO_NUEVA_ALARMA:
                            contAlarmas++;
                            Alarma a = Herramientas.getAlarma(messageRecibido,contAlarmas);
                            clienteActual.nuevaAlarma(a);
                            messageEnvio = Herramientas.generarMensaje(Definiciones.TIPO_NUEVA_ALARMA, cliente,Alarma.getListAlarmasToString(clienteActual.getAlarmas()));
                            break;
                        case Definiciones.TIPO_LISTA_ALARMAS:
                            messageEnvio = Herramientas.generarMensaje(Definiciones.TIPO_NUEVA_ALARMA, cliente,Alarma.getListAlarmasToString(clienteActual.getAlarmas()));
                            break;
                    }
                    System.out.println(messageEnvio);

                    System.out.println(cliente);
                    channel.basicPublish("",cliente, null, messageEnvio.getBytes(StandardCharsets.UTF_8));
                    
                }catch(Exception e){
                    System.out.println(e);
                }
                
            };
            // consume mensaaje de la cola
            channel.basicConsume(QUEUE_NAME_SERVER, true, deliverCallback, consumerTag -> {
            });
        }catch(Exception e){
            System.out.println(e);
        }
        
        
        comprobarAlarmas();
        
    }
    
    private static Cliente nuevoCliente(String cliente){
        for(Cliente c: listClientes){
            if(c.getNombre().equals(cliente)){
                return c;
            }
        }
        Cliente c = new Cliente(cliente);
        listClientes.add(c);
        return c;
    }
    
    private static void actualizarClienteActual(String cliente){
        boolean flag = false;
        for(Cliente c: listClientes){
            if(c.getNombre().equals(cliente)){
                clienteActual = c;
                flag = true;
            }
        }
        if(!flag) clienteActual = null;
    }
    
    public static void comprobarAlarmas(){
        while(true){
            try{
                sleep(20000);
                actualizarEmpresas();
                String messageEnvio;
                for(Cliente c: listClientes){
                    for(Alarma a: c.getAlarmas()){
                        switch(a.getTipo()){
                            case Definiciones.SUBIDA:
                                if(a.getValor()<= a.getEmpresa().getValor()){
                                    messageEnvio = Herramientas.generarMensaje(Definiciones.TIPO_AVISO_ALARMAS, c.getNombre(),a.toString());
                                    channel.basicPublish("",c.getNombre(), null, messageEnvio.getBytes(StandardCharsets.UTF_8));
                                    eliminarAlarmaLista(c,a);
                                }
                                break;
                            case Definiciones.BAJADA:
                                if(a.getValor()>= a.getEmpresa().getValor()){
                                    messageEnvio = Herramientas.generarMensaje(Definiciones.TIPO_AVISO_ALARMAS, c.getNombre(),a.toString());
                                    channel.basicPublish("",c.getNombre(), null, messageEnvio.getBytes(StandardCharsets.UTF_8));
                                }
                                break;
                        }
                    }
                }
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
    
    public static void actualizarEmpresas(){
        listEmpresas = Bolsa.getDatos();
        for(Empresa e: listEmpresas){
            for(Cliente c: listClientes){
                for(Alarma a: c.getAlarmas()){
                    if(a.getEmpresa().getNombre().equals(e.getNombre())){
                        a.setEmpresa(e);
                    }
                }
            }
        }
    }
    
    public static void eliminarAlarmaLista(Cliente cliente, Alarma alarma){
        List<Alarma> alarmas = new ArrayList<>();
        for(Alarma a: cliente.getAlarmas()){
            if(!a.getId().equals(alarma.getId())){
                alarmas.add(a);
            }
        }
        cliente.setAlarmas(alarmas);
    }
}