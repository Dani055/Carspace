import { Navigate } from "react-router-dom";
import { toast } from "react-toastify";

const Protected = ({ loggedUser, children }) => {
console.log(loggedUser)
 if (loggedUser === null) {
    toast.error("You cannot access this page");
    return <Navigate to="/" replace />;
 }
 return children;
};

export default Protected;