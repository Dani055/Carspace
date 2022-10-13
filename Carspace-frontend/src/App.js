import './App.css';
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from 'react-toastify';
import { Route, Routes, BrowserRouter as Router } from "react-router-dom";
import MainPage from './pages/MainPage/MainPage.js';
import NavBar from './components/NavBar/NavBar';
import Footer from './components/Footer/Footer';
import Login from './pages/Login/Login'
import RegisterPage from './pages/RegisterPage/RegisterPage';
import CreateAuction from './pages/CreateAuction/CreateAuction';
import EditAuction from './pages/EditAuction/EditAuction';
import AuctionDetails from './pages/AuctionDetails/AuctionDetails';
import ProfilePage from './pages/ProfilePage/ProfilePage';
import { toast } from 'react-toastify';
import { useEffect, useContext } from 'react';
import { useCookies } from 'react-cookie';
import { checkLoginKey } from './service/userService';
import { UserContext } from './UserProvider';
function App() {
  const [cookies, setCookie, removeCookie] = useCookies();
  const {setLoggedUser} = useContext(UserContext)

  useEffect(()=>{
    if(cookies["token"] !== undefined){
      checkLoginKey().then((res) => {
        setLoggedUser(res.obj);
      })
      .catch((err) => {
        toast.error(err);
        removeCookie('token', {path:'/'});
        setLoggedUser(null);
      })
    }
  }, [])
  return (
    <div className="App">
      <Router>
          <NavBar />
          <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/login" element={<Login />} />
          <Route path="/profile" element={<ProfilePage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/auction/create" element={<CreateAuction />} />
          <Route path="/auction/edit" element={<EditAuction />} />
          <Route path="/auction/details/:auctionId" element={<AuctionDetails />} />
          </Routes>
        <Footer/>
      </Router>
      <ToastContainer hideProgressBar={true}/>
    </div>
  );
}

export default App;
