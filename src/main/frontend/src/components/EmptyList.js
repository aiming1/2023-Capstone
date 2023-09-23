import React from "react";
import styles from "../styles/css/Loading.module.css";

export default () => {
  return (
    <div className={styles.jjimbackground}>
      <img src="img/empty-shopping-cart.svg" alt="빈 카트" width="8%" />
      <div className={styles.loadingText}>
        찜하기를 눌러 마음에 드는 상품을 찜해보세요!
      </div>
    </div>
  );
};
