import './App.css';
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

function App() {
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
          <Route path="/auction/details" element={<AuctionDetails />} />
        </Routes>
        <Footer/>
      </Router>
    </div>
  );
}

export default App;
