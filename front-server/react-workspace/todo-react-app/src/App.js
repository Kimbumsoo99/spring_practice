import logo from "./logo.svg";
import "./App.css";
import Todo from "./Todo";
import { useEffect, useState } from "react";
import { Container, List, Paper } from "@mui/material";
import AddTodo from "./AddTodo";
import { call } from "./ApiService";

function App() {
    const [items, setItem] = useState([]);

    useEffect(() => {
        call("/todo", "GET", null).then((res) => setItem(res.data));
    }, []);

    const requestOptions = {
        method: "GET",
        headers: { "Content-Type": "application/json" },
    };

    const addItem = (item) => {
        call("/todo", "POST", item).then((res) => setItem(res.data));
    };

    const deleteItem = (item) => {
        call("/todo", "DELETE", item).then((res) => setItem(res.data));
    };

    const editItem = (item) => {
        call("/todo", "PUT", item).then((res) => setItem(res.data));
    };

    let todoItems = items.length > 0 && (
        <Paper style={{ margin: 16 }}>
            <List>
                {items.map((item) => (
                    <Todo
                        item={item}
                        key={item.id}
                        editItem={editItem}
                        deleteItem={deleteItem}
                    />
                ))}
            </List>
        </Paper>
    );
    return (
        <div className="App">
            <Container maxWidth="md">
                <AddTodo addItem={addItem} />
                <div className="TodoList">{todoItems}</div>
            </Container>
        </div>
    );
}

export default App;
