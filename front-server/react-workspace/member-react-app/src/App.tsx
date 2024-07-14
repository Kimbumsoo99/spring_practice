import React, { useState } from "react";
import Login from "./TestLogin";
import Signup from "./TestRegister";
import ReIssue from "./ReIssue";
import Test from "./Test";
import TokenRefresher from "./TokenRefresher";
import OAuth2RedirectHandler from "./OAuth2RedirectHandler ";

const App: React.FC = () => {
    const [showLogin, setShowLogin] = useState(true);

    return (
        <div>
            {/* TokenRefresher 컴포넌트를 여기 추가합니다. */}
            <TokenRefresher />
            <OAuth2RedirectHandler />

            <div style={{ textAlign: "center", marginBottom: "20px" }}>
                <button onClick={() => setShowLogin(true)} style={{ marginRight: "10px" }}>
                    Login
                </button>
                <button onClick={() => setShowLogin(false)}>Signup</button>
            </div>
            {showLogin ? <Login /> : <Signup />}
            <ReIssue />
            <Test />
        </div>
    );
};

export default App;
