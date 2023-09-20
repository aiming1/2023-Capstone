import React from "react";
import styles from "../styles/css/Login.module.css";
import { useNavigate } from "react-router-dom";

function Logout({ onClose }) {
  //로그아웃 페이지
    const navigate = useNavigate();
    function logout(){
        localStorage.removeItem("email")
        localStorage.removeItem("name")
        localStorage.removeItem("token")
        onClose();

        alert("로그아웃 되었습니다.");
        navigate("/");
    }

    return (
      <div className={styles.logoutcontainer}>
        <img alt="" src={"img/임시로고.svg"} />
        <br></br>
        <br></br>
        <div className={styles.lh2}> 로그아웃 하시겠습니까? </div>
        <br></br>
        <div>
          <button className={styles.no}
            onClick={() => navigate(-1)}> 취소 </button>
          <button className={styles.yes}
            onClick={logout}> 확인 </button>
        </div>
      </div>
    );
}

export default Logout;