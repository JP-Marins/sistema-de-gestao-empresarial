-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS bd_engenheiros;
USE bd_engenheiros;

-- Tabela de usuários
CREATE TABLE IF NOT EXISTS tb_usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil VARCHAR(30) NOT NULL
);

-- Tabela de clientes
CREATE TABLE IF NOT EXISTS tb_clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome_cliente VARCHAR(100) NOT NULL,
    cpf_cnpj VARCHAR(20) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(100)
);

-- Tabela de engenheiros
CREATE TABLE IF NOT EXISTS tb_engenheiros (
    id_engenheiro INT AUTO_INCREMENT PRIMARY KEY,
    nome_completo VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(100),
    telefone VARCHAR(20)
);

-- Tabela de projetos
CREATE TABLE IF NOT EXISTS tb_projetos (
    id_projeto INT AUTO_INCREMENT PRIMARY KEY,
    nome_projeto VARCHAR(100) NOT NULL,
    data_inicial VARCHAR(10),
    data_final VARCHAR(10),
    engenheiro_responsavel VARCHAR(100),
    status_do_projeto VARCHAR(30)
);

-- Inserção de um usuário administrador padrão
INSERT INTO tb_usuarios (usuario, senha, perfil) 
VALUES ('admin', 'admin123', 'Administrador');
