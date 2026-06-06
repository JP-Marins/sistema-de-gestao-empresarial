-- Criacao do banco de dados
CREATE DATABASE IF NOT EXISTS bd_engenheiros;
USE bd_engenheiros;

-- Tabela para autenticacao de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

-- Tabela para gerenciamento de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    telefone VARCHAR(20),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insercao de usuario administrador padrao
INSERT IGNORE INTO usuarios (usuario, senha) 
VALUES ('admin', 'admin123');