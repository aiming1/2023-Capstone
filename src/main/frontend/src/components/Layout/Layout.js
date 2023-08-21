import Footer from "./Footer";
import Header from "./Header";
import Sidebar from "./Sidebar";
import styles from "../../styles/css/ContentDetail.module.css";
import { Outlet } from "react-router-dom";

const Layout = () => {
  return (
    <div className={styles.wrapper}>
      <div className={styles.contentWrapper}>
        <Header></Header>
        <Outlet></Outlet>
      </div>
      <div className={styles.divsidebar}>
        <Sidebar></Sidebar>
      </div>
      <div>
        <Footer></Footer>
      </div>
    </div>
  );
};

export default Layout;
