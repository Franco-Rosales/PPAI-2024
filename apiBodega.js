const express = require('express');
const { Sequelize, DataTypes } = require('sequelize');
const cors = require('cors');
const bodegas = require('./bodegas.json')

const app = express();
app.use(cors());
app.use(express.json());

const sequelize = new Sequelize('sqlite::memory:');



// Definir el modelo
// const Bodegas 
const Bodegas = sequelize.define('Bodegas', {
    id: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true},
    nombre: {type: DataTypes.STRING},
    fechaUltimaActualizacion: {type: DataTypes.DATEONLY},
    periodicidadActualizacion: {type: DataTypes.INTEGER},
    descripcion: {type: DataTypes.STRING},
    coordenadasUbicacion: {type: DataTypes.JSON},
    historia: {type: DataTypes.STRING},
    },
{ timestamps: false });




async function inicializarBaseDeDatos() {
    await sequelize.sync({ force: true });
    await Bodegas.truncate();
    await Bodegas.bulkCreate(bodegas)
};

// Ruta que recupera todas las bodegas
app.get('/bodegas', async (_, res) => {
    const bodegasTodas = await Bodegas.findAll();
    res.send(bodegasTodas);
});

// Ruta que recupera las bodegas con nombre
app.get('/bodegas/nombre/:criterio', async (req, res) => {
    const criterio = req.params.criterio;
    const nombreBodegas = await Bodegas.findAll({where: { nombre: criterio}});
    res.send(nombreBodegas);

});

// Ruta que recupera las bodegas con fechaActualizacion
app.get('/bodegas/fechaActualizacion/:criterio', async (req, res) => {
    const criterio = req.params.criterio;
    const fechaUpdateBodegas = await Bodegas.findAll({where: { fechaUltimaActualizacion: criterio}});
    res.send(fechaUpdateBodegas);

});





inicializarBaseDeDatos().then(() => {
    app.listen(3000, () => console.log('Servidor en http://localhost:3000'));
});
