import React, { Component } from "react";
import styles from "../styles/css/Login.module.css";
import googlebtn from "../assets/googlebtn.png";
import kakaobtn from "../assets/kakaobtn.png";

class Login extends Component {
  //로그인페이지
  render() {
    return (
      <div className={styles.logincontainer}>
        <br></br>
        <img alt="" src={"img/임시로고.svg"} />
        <br></br>
        <br></br>
        <div className={styles.lh2}> 로그인하여 더 많은 서비스를 이용하기 </div>
        <br></br>
        <div>
          <button
            className={styles.kakao}
            onClick={() => window.open("http://localhost:8080/oauth2/authorization/kakao")}>
            <img src={kakaobtn} width="222" alt="카카오 로그인 버튼" />
          </button>
        </div>
        <br></br>
        <div>
          <button
            className={styles.google}
            onClick={() =>
              window.open("http://localhost:8080/oauth2/authorization/google")
            }
          >
            <img src={googlebtn} width="222" alt="구글 로그인 버튼" />
          </button>
        </div>
        <div className={styles.guideline}>
          ______________________________________________________
        </div>
        <span className={styles.guide}> 도움이 필요하시면 </span>
        <span className={styles.guide2}>이메일</span>
        <span className={styles.guide}>로 연락 부탁드립니다. </span>
      </div>
    );
  }
}

export default Login;