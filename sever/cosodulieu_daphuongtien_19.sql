-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 29, 2016 at 05:06 PM
-- Server version: 10.1.10-MariaDB
-- PHP Version: 5.6.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cosodulieu_daphuongtien_19`
--

-- --------------------------------------------------------

--
-- Table structure for table `audio`
--

CREATE TABLE `audio` (
  `id` int(11) NOT NULL,
  `title` text CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL,
  `url` text CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `audio`
--

INSERT INTO `audio` (`id`, `title`, `url`) VALUES
(1, 'Đường đến ngày vinh quang', 'hecsdldpt19/resources/%C4%90%C6%B0%E1%BB%9Dng%20%C4%90%E1%BA%BFn%20Ng%C3%A0y%20Vinh%20Quang.mp3'),
(2, 'Nơi Tình Yêu Bắt Đầu', 'hecsdldpt19/resources/Noi%20Tinh%20Yeu%20Bat%20Dau.mp3'),
(4, 'Yếu Đuối', 'hecsdldpt19/resources/Yeu%20Duoi.mp3'),
(5, 'Thật Bất Ngờ', 'hecsdldpt19/resources/That%20Bat%20Ngo.mp3'),
(6, 'Gangnam Style', 'hecsdldpt19/resources/Gangnam%20Style.mp3'),
(7, 'Có Phải Em Mùa Thu Hà Nội', 'hecsdldpt19/resources/Co%20Phai%20Em%20MuaThu%20Ha%20Noi.mp3'),
(9, 'Sau Tất Cả', 'hecsdldpt19/resources/Sau%20Tat%20Ca.mp3'),
(10, 'Let Her Go', 'hecsdldpt19/resources/Let%20Her%20Go.mp3'),
(11, 'Một Nhà', 'hecsdldpt19/resources/M%E1%BB%99t%20Nh%C3%A0.mp3'),
(12, 'Bố ơi, Mình đi đâu thế', 'hecsdldpt19/resources/B%E1%BB%91%20%C6%A0i,%20M%C3%ACnh%20%C4%90i%20%C4%90%C3%A2u%20Th%E1%BA%BF.mp3'),
(13, 'My Everything', 'hecsdldpt19/resources/My%20Everything.mp3');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `audio`
--
ALTER TABLE `audio`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `audio`
--
ALTER TABLE `audio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
