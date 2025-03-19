import './App.css'
import Home from './pages/Home.jsx'
import {Route, Routes} from "react-router-dom";
import Favorite from "./pages/Favorites.jsx";
import NavBar from "./components/NavBar.jsx";
import ApiCall from "./pages/ApiCall.jsx";

function App() {
    return (
        <>
            <div><NavBar/></div>
            <main className="App">
                <Routes>
                    <Route path="/" element={<Home/>}></Route>
                    <Route path="/favorites" element={<Favorite/>}></Route>
                    <Route path="/api-call" element={<ApiCall/>}></Route>
                </Routes>
            </main>
        </>
    )
}

export default App