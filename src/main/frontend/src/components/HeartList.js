import React, { useState, useEffect } from "react";
import axios from "axios";
import styles from "../styles/css/ItemList.module.css";
import Pagination from "./Pagination";

//글자수 제한 함수
const truncate = (str, n) => {
  return str?.length > n ? str.substr(0, n - 1) + "..." : str;
};

const HeartList = () => {};

export default HeartList;
