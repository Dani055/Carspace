
import axiosConfig from "../axiosConfig"

const createBidCall = (body, auctionId) => {
    return axiosConfig.post(`bid/`+auctionId, body)
        .then((response) => response.data)
        .catch((err) => {
            throwError(err)
        })
}


function throwError (err){
    if (err.response) {
        throw err.response.data.message;
    } else if (err.request) {
        throw err.request;
    } else {
        throw err;
    }
}

export {
    createBidCall
}