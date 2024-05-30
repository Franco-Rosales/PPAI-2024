const express = require('express');
const { Sequelize, DataTypes } = require('sequelize');
const cors = require('cors');

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
    await Bodegas.bulkCreate([
        {
            nombre: "Bodega Catena Zapata",
            fechaUltimaActualizacion: new Date("2024-05-30"),
            periodicidadActualizacion: 30,
            descripcion: "Bodega dedicada a la producción de vinos tintos.",
            coordenadasUbicacion: JSON.stringify([40.7128, -74.0060]),
            historia: "Fundada en 1920, esta bodega ha sido pionera en la producción de vinos orgánicos."
        },
        {
            nombre: "Bodega Salentein",
            fechaUltimaActualizacion: new Date("2024-05-30"),
            periodicidadActualizacion: 45,
            descripcion: "Bodega especializada en vinos blancos y rosados.",
            coordenadasUbicacion: JSON.stringify([34.0522, -118.2437]),
            historia: "Con más de 50 años de historia, Bodega Salentein es reconocida por sus técnicas innovadoras de vinificación."
        },
        {
            nombre: "Bodega Norton",
            fechaUltimaActualizacion: new Date("2024-01-10"),
            periodicidadActualizacion: 60,
            descripcion: "Bodega con una amplia gama de vinos espumosos.",
            coordenadasUbicacion: JSON.stringify([41.9028, 12.4964]),
            historia: "Establecida en 1880, Bodega Norton ha ganado numerosos premios internacionales por sus vinos de alta calidad."
        },
        {
            nombre: "Bodega Luigi Bosca",
            fechaUltimaActualizacion: new Date("2024-05-29"),
            periodicidadActualizacion: 30,
            descripcion: "Bodega histórica con una rica tradición en la producción de vinos finos.",
            coordenadasUbicacion: JSON.stringify([38.7223, -9.1393]),
            historia: "Desde 1901, Bodega Luigi Bosca ha sido un referente en la industria vitivinícola argentina."
        },
        {
            nombre: "Bodega Trapiche",
            fechaUltimaActualizacion: new Date("2024-02-20"),
            periodicidadActualizacion: 45,
            descripcion: "Una de las bodegas más importantes de Argentina, conocida por su innovación.",
            coordenadasUbicacion: JSON.stringify([51.5074, -0.1278]),
            historia: "Bodega Trapiche, fundada en 1883, ha sido pionera en la exportación de vinos argentinos."
        },
        {
            nombre: "Bodega Zuccardi",
            fechaUltimaActualizacion: new Date("2024-03-05"),
            periodicidadActualizacion: 60,
            descripcion: "Bodega familiar que se destaca por su enfoque en la sostenibilidad.",
            coordenadasUbicacion: JSON.stringify([48.8566, 2.3522]),
            historia: "Bodega Zuccardi ha sido reconocida globalmente por su compromiso con la sostenibilidad y la calidad."
        },
        {
            nombre: "Bodega Ruca Malen",
            fechaUltimaActualizacion: new Date("2024-01-20"),
            periodicidadActualizacion: 30,
            descripcion: "Bodega boutique que ofrece una experiencia única de maridaje.",
            coordenadasUbicacion: JSON.stringify([35.6895, 139.6917]),
            historia: "Desde 1998, Bodega Ruca Malen ha sido conocida por su enfoque en la creación de experiencias enoturísticas."
        },
        {
            nombre: "Bodega Achaval Ferrer",
            fechaUltimaActualizacion: new Date("2024-04-10"),
            periodicidadActualizacion: 45,
            descripcion: "Bodega reconocida por la alta calidad de sus Malbec.",
            coordenadasUbicacion: JSON.stringify([40.7306, -73.9352]),
            historia: "Bodega Achaval Ferrer, fundada en 1998, ha ganado numerosos premios por sus vinos de alta gama."
        },
        {
            nombre: "Bodega Terrazas de los Andes",
            fechaUltimaActualizacion: new Date("2024-03-01"),
            periodicidadActualizacion: 60,
            descripcion: "Bodega que produce vinos en terrazas a diferentes altitudes.",
            coordenadasUbicacion: JSON.stringify([37.7749, -122.4194]),
            historia: "Con más de 20 años de historia, Bodega Terrazas de los Andes se ha destacado por sus vinos de altura."
        },
        {
            nombre: "Bodega Séptima",
            fechaUltimaActualizacion: new Date("2024-02-10"),
            periodicidadActualizacion: 30,
            descripcion: "Bodega moderna con una arquitectura impresionante.",
            coordenadasUbicacion: JSON.stringify([34.0522, -118.2437]),
            historia: "Bodega Séptima, parte del grupo Codorníu, se destaca por su diseño arquitectónico y sus vinos de calidad."
        },
        {
            nombre: "Bodega Andeluna",
            fechaUltimaActualizacion: new Date("2024-01-05"),
            periodicidadActualizacion: 45,
            descripcion: "Bodega situada en los Andes con vinos elegantes.",
            coordenadasUbicacion: JSON.stringify([51.1657, 10.4515]),
            historia: "Desde 2003, Bodega Andeluna ha sido sinónimo de elegancia y calidad en la región de Mendoza."
        },
        {
            nombre: "Bodega Viña Cobos",
            fechaUltimaActualizacion: new Date("2024-03-22"),
            periodicidadActualizacion: 60,
            descripcion: "Bodega de autor que produce vinos premium.",
            coordenadasUbicacion: JSON.stringify([41.8781, -87.6298]),
            historia: "Fundada por el enólogo Paul Hobbs, Bodega Viña Cobos es conocida por sus vinos de alta gama."
        },
        {
            nombre: "Bodega Vistalba",
            fechaUltimaActualizacion: new Date("2024-02-15"),
            periodicidadActualizacion: 30,
            descripcion: "Bodega que combina tradición y modernidad en sus vinos.",
            coordenadasUbicacion: JSON.stringify([48.1351, 11.5820]),
            historia: "Bodega Vistalba, fundada en 2003, se ha destacado por su enfoque en la calidad y la innovación."
        },
        {
            nombre: "Bodega Rutini Wines",
            fechaUltimaActualizacion: new Date("2024-01-25"),
            periodicidadActualizacion: 45,
            descripcion: "Una de las bodegas más antiguas y prestigiosas de Argentina.",
            coordenadasUbicacion: JSON.stringify([40.4168, -3.7038]),
            historia: "Fundada en 1885, Bodega Rutini Wines ha sido un pilar en la industria vitivinícola argentina."
        },
        {
            nombre: "Bodega El Enemigo",
            fechaUltimaActualizacion: new Date("2024-04-15"),
            periodicidadActualizacion: 60,
            descripcion: "Bodega innovadora que desafía las normas tradicionales.",
            coordenadasUbicacion: JSON.stringify([52.5200, 13.4050]),
            historia: "Bodega El Enemigo, creada por Alejandro Vigil, es conocida por sus vinos audaces y únicos."
        },
        {
            nombre: "Bodega Finca La Anita",
            fechaUltimaActualizacion: new Date("2024-03-10"),
            periodicidadActualizacion: 30,
            descripcion: "Bodega boutique con una producción limitada y de alta calidad.",
            coordenadasUbicacion: JSON.stringify([59.3293, 18.0686]),
            historia: "Desde 1992, Bodega Finca La Anita ha sido sinónimo de excelencia y exclusividad en Mendoza."
        },
        
    ]);
};

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

// Ruta que recupera todas las bodegas
app.get('/bodegas', async (_, res) => {
    const bodegas = await Bodegas.findAll();
    res.send(bodegas);
});



inicializarBaseDeDatos().then(() => {
    app.listen(3000, () => console.log('Servidor en http://localhost:3000'));
});
