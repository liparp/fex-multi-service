import React, { useState } from 'react';


function ApiCall(){
    const [textareaValue, setTextareaValue] = useState('');

    function submitForm(e) {
        e.preventDefault();
        console.log('submitted');
        console.log(textareaValue);
        // Make an API call with the textarea value
        fetch('https://api.example.com/endpoint', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ data: textareaValue })
        })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }

    return <>
        <div>
            <form onSubmit={submitForm}>
                <textarea
                    value={textareaValue}
                    onChange={(e) => setTextareaValue(e.target.value)}
                />
                <button type="submit">Submit</button>
            </form>
        </div>
    </>
}

export default ApiCall;