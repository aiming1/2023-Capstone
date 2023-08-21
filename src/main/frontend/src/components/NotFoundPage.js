import styles from "../styles/css/NotFoundPage.module.css";
import { useEffect, useState } from "react";

const NotFound = () => {
  //링크없음 페이지
  function timeout() {
    setTimeout(()=>{
        window.location.href = "/";
    }, 3000);
  }

  let [time, setTime] = useState(3);

  function timer() {
    setTimeout(()=>{
        let last = time;
        last -= 1;
        setTime(last);
    }, 1000)
  }

  useEffect(()=>{
  timer()})


  return (
    <div className={styles.div}>
      <div className={styles.div1}>
        <span>게시글이</span>
        <span className={styles.span}> 삭제</span>
        <span className={styles.span1}>되었거나</span>
        <span className={styles.span}> 존재하지 않아요</span>
        <span> :(</span>
      </div>
      <div className={styles.div2}>{time}초 뒤 홈으로 돌아갑니다</div>{timeout()}
    </div>
  );
};

export default NotFound;
