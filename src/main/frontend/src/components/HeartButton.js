import React from "react";
import styles from "../styles/css/ContentDetail.module.css";
import FullStartImg from "../assets/star_full.svg";
import EmptyStarImg from "../assets/star_light.svg";

const HeartButton = ({ heart, onClick }) => {
  return (
    <img
      className={styles.starButton}
      src={heart ? FullStartImg : EmptyStarImg}
      onClick={onClick}
    />
  );
};

export default HeartButton;
