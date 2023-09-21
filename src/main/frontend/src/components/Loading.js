import React from 'react';
import styles from "../styles/css/Loading.module.css";
import Spinner from '../assets/spinner.gif';

export default () => {
  return (
    <div className={styles.background}>
    <img src={Spinner} alt="로딩중" width="5%" style={{ marginTop: '50px' }}/>
      <div className={styles.loadingText}>로딩 중...</div>
    </div>
  );
};