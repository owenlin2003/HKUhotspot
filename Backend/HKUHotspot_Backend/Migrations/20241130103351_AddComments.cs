using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace HKUHotspot_Backend.Migrations
{
    /// <inheritdoc />
    public partial class AddComments : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "HKUStationComments",
                columns: table => new
                {
                    StationID = table.Column<int>(type: "int", nullable: false),
                    CommentAuthor = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    CommentDescription = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                });
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "HKUStationComments");
        }
    }
}
