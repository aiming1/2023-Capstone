import styles from "../../styles/css/Header.module.css";
import Searchbar from "./Searchbar";
import { useNavigate } from "react-router-dom";

const Header = () => {
  const navigate = useNavigate();
  function loginlink() {
    if (!localStorage.getItem("token")) navigate("/login");
    else navigate("/logout");
  }

  function heartlink() {
    if (!localStorage.getItem("token")) {alert("로그인 후 이용해주세요!"); navigate("/login");}
    else navigate("/heartList");
  }

  return (
    <div className={styles.groupParent}>
      <div className={styles.planetParent}>
        <button className={styles.planet} onClick={() => {navigate("/")
                window.location.reload()}}>
          <img src="/img/임시로고.svg" style={{ width: '200px' }}/>
        </button>
      </div>
      <div
        className={styles.basketAlt3Icon}
        onClick={heartlink}
      >
        <img src="/img/basket-alt-3.svg" />
      </div>
      <button className={styles.userCicrleDuotoneIcon} onClick={loginlink}>
        <img src="/img/user-cicrle-duotone.svg" />
      </button>
      <Searchbar></Searchbar>
      <div className={styles.line} />
      {/*<div className={styles.groupItem} />
      <img className={styles.searchAltIcon} alt="" src="/img/search-alt.svg" />
      <input className={styles.div16}
        placeholder="상품명 입력"
        />*/}
    </div>
  );
};

export default Header;