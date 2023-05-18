-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mer. 17 mai 2023 à 16:11
-- Version du serveur : 11.1.0-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `travel`
--
CREATE DATABASE IF NOT EXISTS `travel` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
GRANT USAGE ON *.* TO 'traveler'@localhost IDENTIFIED BY 'world1997';
GRANT ALL privileges ON `travel`.* TO 'traveler'@localhost;
FLUSH PRIVILEGES;
USE `travel`;

-- --------------------------------------------------------

--
-- Structure de la table `airport`
--

CREATE TABLE `airport` (
  `id` int(11) NOT NULL,
  `city_a` int(11) DEFAULT NULL,
  `city_b` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `airport`
--

INSERT INTO `airport` (`id`, `city_a`, `city_b`) VALUES
(1, 2, 3),
(2, 2, 4),
(3, 3, 2),
(4, 4, 2),
(5, 3, 4),
(6, 4, 3);

-- --------------------------------------------------------

--
-- Structure de la table `city`
--

CREATE TABLE `city` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `train` tinyint(1) DEFAULT NULL,
  `plane` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `city`
--

INSERT INTO `city` (`id`, `name`, `train`, `plane`) VALUES
(1, 'Anaheim', 1, 0),
(2, 'Marne-la-Vallee', 1, 1),
(3, 'Lantau Island', 0, 1),
(4, 'Pudong New District', 1, 1),
(5, 'Urayasu', 1, 0);

-- --------------------------------------------------------

--
-- Structure de la table `parks`
--

CREATE TABLE `parks` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `parks`
--

INSERT INTO `parks` (`id`, `name`, `city`) VALUES
(1, 'Disneyland Park', 'Anaheim'),
(2, 'Walt Disney Studios Park', 'Marne-la-Vallee'),
(3, 'Hong Kong Disneyland Park', 'Lantau Island'),
(4, 'Shanghai Disneyland Park', 'Pudong New District'),
(5, 'Tokyo Disneyland Park', 'Urayasu');

-- --------------------------------------------------------

--
-- Structure de la table `trainstation`
--

CREATE TABLE `trainstation` (
  `id` int(11) NOT NULL,
  `city_a` int(11) DEFAULT NULL,
  `city_b` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `trainstation`
--

INSERT INTO `trainstation` (`id`, `city_a`, `city_b`) VALUES
(1, 1, 2),
(2, 2, 1),
(3, 1, 4),
(4, 4, 1),
(5, 1, 5),
(6, 5, 1),
(7, 2, 4),
(8, 4, 2),
(9, 2, 5),
(10, 5, 2),
(11, 4, 5),
(12, 5, 4);

-- --------------------------------------------------------

--
-- Structure de la table `travel`
--

CREATE TABLE `travel` (
  `id` int(11) NOT NULL,
  `access_type` varchar(255) DEFAULT NULL,
  `park_name` varchar(255) DEFAULT NULL,
  `city_start` varchar(255) DEFAULT NULL,
  `city_end` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `travel`
--

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `airport`
--
ALTER TABLE `airport`
  ADD PRIMARY KEY (`id`),
  ADD KEY `city_a` (`city_a`),
  ADD KEY `city_b` (`city_b`);

--
-- Index pour la table `city`
--
ALTER TABLE `city`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `parks`
--
ALTER TABLE `parks`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `trainstation`
--
ALTER TABLE `trainstation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `city_a` (`city_a`),
  ADD KEY `city_b` (`city_b`);

--
-- Index pour la table `travel`
--
ALTER TABLE `travel`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `airport`
--
ALTER TABLE `airport`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `city`
--
ALTER TABLE `city`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `parks`
--
ALTER TABLE `parks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `trainstation`
--
ALTER TABLE `trainstation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `travel`
--
ALTER TABLE `travel`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `airport`
--
ALTER TABLE `airport`
  ADD CONSTRAINT `airport_ibfk_1` FOREIGN KEY (`city_a`) REFERENCES `city` (`id`),
  ADD CONSTRAINT `airport_ibfk_2` FOREIGN KEY (`city_b`) REFERENCES `city` (`id`);

--
-- Contraintes pour la table `trainstation`
--
ALTER TABLE `trainstation`
  ADD CONSTRAINT `trainstation_ibfk_1` FOREIGN KEY (`city_a`) REFERENCES `city` (`id`),
  ADD CONSTRAINT `trainstation_ibfk_2` FOREIGN KEY (`city_b`) REFERENCES `city` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
