-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 05, 2019 lúc 08:32 AM
-- Phiên bản máy phục vụ: 10.1.38-MariaDB
-- Phiên bản PHP: 5.6.40

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `genealogy`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `description_member`
--

CREATE TABLE `description_member` (
  `description_member_id` int(11) NOT NULL,
  `nickname` text COLLATE utf8_unicode_ci NOT NULL,
  `degree` text COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `extra_data` text COLLATE utf8_unicode_ci NOT NULL,
  `birthday` date NOT NULL,
  `dead_day` date NOT NULL,
  `address` text COLLATE utf8_unicode_ci NOT NULL,
  `node_member_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `genealogy`
--

CREATE TABLE `genealogy` (
  `genealogy_id` int(11) NOT NULL,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `history` text COLLATE utf8_unicode_ci NOT NULL,
  `thuy_to` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `node_member`
--

CREATE TABLE `node_member` (
  `node_member_id` int(11) NOT NULL,
  `pedigree_id` int(11) NOT NULL,
  `genealogy_id` int(11) NOT NULL,
  `parent_id` int(11) NOT NULL,
  `mother_id` int(11) NOT NULL,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `gender` int(11) NOT NULL,
  `child_index` int(11) NOT NULL,
  `life_index` int(11) NOT NULL,
  `patch_key` text COLLATE utf8_unicode_ci NOT NULL,
  `image` text COLLATE utf8_unicode_ci NOT NULL,
  `parent_relation` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `pedigree`
--

CREATE TABLE `pedigree` (
  `pedigree_id` int(11) NOT NULL,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `history` int(11) NOT NULL,
  `genealogy_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `permission`
--

CREATE TABLE `permission` (
  `permission_id` int(11) NOT NULL,
  `name` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role`
--

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `name` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `gender` text COLLATE utf8_unicode_ci NOT NULL,
  `email` text COLLATE utf8_unicode_ci NOT NULL,
  `birthday` date NOT NULL,
  `address` text COLLATE utf8_unicode_ci NOT NULL,
  `phone` text COLLATE utf8_unicode_ci NOT NULL,
  `image` text COLLATE utf8_unicode_ci NOT NULL,
  `password` text COLLATE utf8_unicode_ci NOT NULL,
  `active` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_permission`
--

CREATE TABLE `user_permission` (
  `user_permission_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `genealogy_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  `pedigree_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `description_member`
--
ALTER TABLE `description_member`
  ADD PRIMARY KEY (`description_member_id`),
  ADD KEY `node_member_id` (`node_member_id`);

--
-- Chỉ mục cho bảng `genealogy`
--
ALTER TABLE `genealogy`
  ADD PRIMARY KEY (`genealogy_id`);

--
-- Chỉ mục cho bảng `node_member`
--
ALTER TABLE `node_member`
  ADD PRIMARY KEY (`node_member_id`);

--
-- Chỉ mục cho bảng `pedigree`
--
ALTER TABLE `pedigree`
  ADD PRIMARY KEY (`pedigree_id`),
  ADD KEY `genealogy_id` (`genealogy_id`);

--
-- Chỉ mục cho bảng `permission`
--
ALTER TABLE `permission`
  ADD PRIMARY KEY (`permission_id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- Chỉ mục cho bảng `user_permission`
--
ALTER TABLE `user_permission`
  ADD PRIMARY KEY (`user_permission_id`),
  ADD KEY `genealogy_id` (`genealogy_id`),
  ADD KEY `pedigree_id` (`pedigree_id`),
  ADD KEY `user_id` (`user_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `description_member`
--
ALTER TABLE `description_member`
  ADD CONSTRAINT `description_member_ibfk_1` FOREIGN KEY (`node_member_id`) REFERENCES `node_member` (`node_member_id`);

--
-- Các ràng buộc cho bảng `pedigree`
--
ALTER TABLE `pedigree`
  ADD CONSTRAINT `pedigree_ibfk_1` FOREIGN KEY (`genealogy_id`) REFERENCES `genealogy` (`genealogy_id`);

--
-- Các ràng buộc cho bảng `user_permission`
--
ALTER TABLE `user_permission`
  ADD CONSTRAINT `user_permission_ibfk_1` FOREIGN KEY (`genealogy_id`) REFERENCES `genealogy` (`genealogy_id`),
  ADD CONSTRAINT `user_permission_ibfk_2` FOREIGN KEY (`pedigree_id`) REFERENCES `pedigree` (`pedigree_id`),
  ADD CONSTRAINT `user_permission_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
