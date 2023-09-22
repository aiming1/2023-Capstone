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
        <a href="/">
          <img alt="" src={"img/임시로고.svg"} className={styles.logoimg}/>
        </a>
        <br/>
        <div className={styles.lih2}> 로그인하기 </div>
        <br/>
        <div className={styles.loginbox}>
          <div className={styles.buttons}>
            <div>
              <button
                className={styles.kakao}
                onClick={() => window.open("http://localhost:8080/oauth2/authorization/kakao")}>
                <span>KAKAO 로그인</span>
                {/*<img src={kakaobtn} width="222" alt="카카오 로그인 버튼" />*/}
              </button>
            </div>
            <br/>
            <div>
              <button
                className={styles.google}
                onClick={() =>
                window.open("http://localhost:8080/oauth2/authorization/google")}>
                <span>GOOGLE 로그인</span>
                {/*<img src={googlebtn} width="222" alt="구글 로그인 버튼" />*/}
              </button>
            </div>
          </div>

          <div className={styles.guideline}>
            _____________________________________________________________________
          </div>

        </div>
        <span className={styles.guide}> 도움이 필요하시면 </span>
        <span className={styles.guide2}>이메일</span>
        <span className={styles.guide}>로 연락 부탁드립니다. </span>
      </div>
    );
  }
}

export default Login;