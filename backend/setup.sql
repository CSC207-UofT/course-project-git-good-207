-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Nov 10, 2021 at 10:15 PM
-- Server version: 5.7.34
-- PHP Version: 7.4.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `foodstagram`
--

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
                            `user_id` text NOT NULL,
                            `post_id` text NOT NULL,
                            `comment_time` timestamp NULL DEFAULT NULL,
                            `comment_text` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`user_id`, `post_id`, `comment_time`, `comment_text`, `comment_id`) VALUES
    ('1', 'b', '2008-11-11 16:44:33', 'wow I love your post', 'xyz');

-- --------------------------------------------------------

--
-- Table structure for table `follows`
--

CREATE TABLE `follows` (
                           `user_id` text NOT NULL,
                           `follower_id` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `follows`
--

INSERT INTO `follows` (`user_id`, `follower_id`) VALUES
    ('1', '2');

-- --------------------------------------------------------

--
-- Table structure for table `likes`
--

CREATE TABLE `likes` (
                         `user_id` text NOT NULL,
                         `post_id` text NOT NULL,
                         `category` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `likes`
--

INSERT INTO `likes` (`user_id`, `post_id`, `category`) VALUES
    ('1', 'b', 'italian');

-- --------------------------------------------------------

--
-- Table structure for table `posts`
--

CREATE TABLE `posts` (
                         `post_id` text NOT NULL,
                         `user_id` text NOT NULL,
                         `recipe_id` text NOT NULL,
                         `category` text NOT NULL,
                         `posted_time` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`post_id`, `user_id`, `recipe_id`, `category`, `posted_time`) VALUES
                                                                                       ('a', '1', 'z', 'chinese', '2008-11-11 16:12:01'),
                                                                                       ('b', '2', 'x', 'italian', '2008-01-01 17:55:15');

-- --------------------------------------------------------

--
-- Table structure for table `recipes`
--

CREATE TABLE `recipes` (
                           `recipe_id` text NOT NULL,
                           `title` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `recipes`
--

INSERT INTO `recipes` (`recipe_id`, `title`) VALUES
                                                 ('z', 'My new recipe title!2021-11-10T17:07:36.676543'),
                                                 ('x', 'italian recipe');

-- --------------------------------------------------------

--
-- Table structure for table `recipes_steps`
--

CREATE TABLE `recipes_steps` (
                                 `recipe_id` text NOT NULL,
                                 `step_number` int(11) NOT NULL,
                                 `step_text` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `recipes_steps`
--

INSERT INTO `recipes_steps` (`recipe_id`, `step_number`, `step_text`) VALUES
                                                                          ('x', 1, 'Just make the italian recipe!'),
                                                                          ('z', 0, 'The first step is to make the dish.'),
                                                                          ('z', 1, 'The second step is to finish making the dish.');

-- --------------------------------------------------------

--
-- Table structure for table `recipe_ingredients`
--

CREATE TABLE `recipe_ingredients` (
                                      `recipe_id` text NOT NULL,
                                      `ingredient_name` text NOT NULL,
                                      `ingredient_count` float DEFAULT NULL,
                                      `ingredient_amount` float DEFAULT NULL,
                                      `ingredient_measurement` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `recipe_ingredients`
--

INSERT INTO `recipe_ingredients` (`recipe_id`, `ingredient_name`, `ingredient_count`, `ingredient_amount`, `ingredient_measurement`) VALUES
                                                                                                                                         ('z', 'apple', 20000, NULL, NULL),
                                                                                                                                         ('z', 'awesome sauce', NULL, 5, 'tbsp');

-- --------------------------------------------------------

--
-- Table structure for table `user_info`
--

CREATE TABLE `user_info` (
                             `user_id` text NOT NULL,
                             `username` text NOT NULL,
                             `password` text NOT NULL,
                             `bio` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_info`
--

INSERT INTO `user_info` (`user_id`, `username`, `password`, `bio`) VALUES
                                                                       ('1', 'eric', '123', 'hi im eric'),
                                                                       ('2', 'shawn', '1234', 'hi im shawn');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
