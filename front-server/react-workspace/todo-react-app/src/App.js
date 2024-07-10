import logo from "./logo.svg";
import "./App.css";
import Todo from "./Todo";
import React, { useEffect, useState } from "react";
import { Container, List, Paper, Grid, Button, AppBar, Toolbar, Typography } from "@mui/material";
import AddTodo from "./AddTodo";
import { call, signout } from "./ApiService";

function App() {
    const [items, setItem] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        call("/todo", "GET", null).then((res) => {
            setItem(res.data);
            setLoading(false);
        });
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
                    <Todo item={item} key={item.id} editItem={editItem} deleteItem={deleteItem} />
                ))}
            </List>
        </Paper>
    );

    let navigationBar = (
        <AppBar position="static">
            <Toolbar>
                <Grid justifyContent="space-between" container>
                    <Grid item>
                        <Typography variant="h6">오늘의 할일</Typography>
                    </Grid>
                    <Grid item>
                        <Button color="inherit" raised onClick={signout}>
                            로그아웃
                        </Button>
                    </Grid>
                </Grid>
            </Toolbar>
        </AppBar>
    );

    let todoListPage = (
        <div>
            {navigationBar}
            <Container maxWidth="md">
                <AddTodo addItem={addItem} />
                <div className="TodoList">{todoItems}</div>
            </Container>
        </div>
    );

    let loadingPage = <h1> 로딩중... </h1>;
    let content = loadingPage;

    if (!loading) {
        content = todoListPage;
    }

    return <div className="App">{content}</div>;
}

export default App;
