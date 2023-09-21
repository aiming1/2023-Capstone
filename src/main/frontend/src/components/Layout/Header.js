import styles from "../../styles/css/Header.module.css";
import Searchbar from "./Searchbar";
import LoginModal from "../Login.js";
import LogoutModal from "../Logout.js";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const Header = () => {
  const navigate = useNavigate();
  const [isLogoutModalOpen, setIsLogoutModalOpen] = useState(false);

  /*function loginlink() {
    if (!localStorage.getItem("token")) navigate("/login");
    else navigate("/logout");
  }*/
  function loginlink() {
    if (!localStorage.getItem("token")) {
      // 모달을 열기 위해 openModal 함수 호출
      navigate("/login");
    } else {
      setIsLogoutModalOpen(true);
    }
  }

  function heartlink() {
    if (!localStorage.getItem("token")) {
      alert("로그인 후 이용해주세요!");
      navigate("/login");
    } else navigate("/heartList");
  }

  return (
    <div className={styles.groupParent}>
      <div className={styles.planetParent}>
        <button
          className={styles.planet}
          onClick={() => {
            navigate("/");
            window.location.reload();
          }}
        >
          <img src="/img/임시로고.svg" />
        </button>
      </div>

      <div className={styles.cartGroup}>
        <div className={styles.basketAlt3Icon} onClick={heartlink}>
          <img src="/img/Favorite-Cart.svg" />
        </div>
        <div className={styles.line} />
        <button className={styles.userCicrleDuotoneIcon} onClick={loginlink}>
          <img src="/img/user-cicrle-duotone.svg" />
        </button>
        {isLogoutModalOpen && (
          <LogoutModal onClose={() => setIsLogoutModalOpen(false)} />
        )}
        <Searchbar></Searchbar>

        {/*<div className={styles.groupItem} />
      <img className={styles.searchAltIcon} alt="" src="/img/search-alt.svg" />
      <input className={styles.div16}
        placeholder="상품명 입력"
        />*/}
      </div>
    </div>
  );
};

export default Header;
