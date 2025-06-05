package socket_interaction;

import socket_interaction.errors.ClientAlreadyRun;
import socket_interaction.errors.ClientIsStoped;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SocketManager {
    // Private variables
    private final String address;
    private final int port;

    private boolean is_run = false;
    private Socket socket;
    DataOutputStream output_stream;
    DataInputStream input_stream;

    // Public Functions
    public SocketManager(String address, int port) {
        this.address = address;
        this.port = port;
    }
    public void run() throws Exception {
        if (is_run) throw new ClientAlreadyRun();

        socket = new Socket(address, port);
        output_stream = new DataOutputStream(socket.getOutputStream());
        input_stream = new DataInputStream(socket.getInputStream());

        is_run = true;
    }
    public void stop() throws Exception{
        if(is_run)
        {
            socket.close();
            is_run = false;
        }
    }
    public void send(String message) throws Exception{
        if(is_run) {
            byte[] message_bytes = message.getBytes();

            output_stream.write(message_bytes);
        }
        else {
            throw new ClientIsStoped();
        }
    }
    public byte readByte() throws Exception {
        if(is_run) {
            return input_stream.readByte();
        }
        else {
            throw new ClientIsStoped();
        }
    }
    public byte[] readAllBytes() throws Exception {
        if(is_run) {
            return input_stream.readAllBytes();
        }
        else {
            throw new ClientIsStoped();
        }
    }
    public String read() throws Exception{
        if(is_run) {
            String response = new String(input_stream.readAllBytes(), StandardCharsets.UTF_8);
            long end = response.indexOf('\000');
            if(end != -1)
            {
                response = response.substring(0, response.indexOf('\000'));
            }
            return response;
        }
        else {
            throw new ClientIsStoped();
        }
    }
}


