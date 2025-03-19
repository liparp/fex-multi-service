import MovieCard from "../components/MovieCard";
import {useState} from "react";

function Home() {
    const [searchQuery, setSearchQuery] = useState('');
    const movies = [{title: 'movie bich', url: 'bich', description: 'fex movie desc'}, {
        title: 'movie bich 2',
        url: 'bich',
        description: 'fex movie desc 2'
    }];

    const handleSearch = (e) => {
        e.preventDefault()
        console.log(searchQuery);
    }

    return <div>
        <form onSubmit={handleSearch}>
            <input type={"text"} placeholder={'Search movie'}
                   value={searchQuery}
                   onChange={(e) => setSearchQuery(e.target.value)}/>
            <button type="submit">Search</button>
        </form>
        {movies.map(movie => movie.title.toLowerCase().startsWith(searchQuery) &&
            <MovieCard key={movie.title} movie={movie}/>)}
    </div>
}

export default Home;